package com.example.calendar

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.*

const val html_url = "http://fkn.omsu.ru/academics/Schedule/schedule4_1.htm"

typealias TableRow = List<String?>
typealias SpanInfo = Pair<Int, String>

fun span2Int(cell: Element, span: String, default: Int = 1): Int {
    var result = 0
    try {
        val value = cell.attr(span)
        result = value.toInt()
    }
    catch (e: Exception) { ////////////// не любое исключение
        result = default
    }
    return result
}

fun parse_schedule(): MutableList<TableRow> {
    val result = mutableListOf<TableRow>()
    val doc: Document = Jsoup.connect(html_url).get()
    val table = doc.select("table").get(0)
    val tbody = table.select("tbody")[0]
    val all_rows = tbody.select("tr")
    val head = all_rows[0].select("td")
    result.add(head.map{ it.text() })
    val width = head.size

    val prev_row_info = MutableList<SpanInfo?>(width) { null }

    for ((row_i, row) in all_rows.drop(1).withIndex()) {
        val cells = row.select("td")
        // preparing result row
        val result_row = mutableListOf<String?>()
        for ((j, cell_info) in prev_row_info.withIndex()) {
            var data: String? = null
            if (cell_info != null) {
                val (cnt, tmp_data) = cell_info
                prev_row_info[j] = if (cnt == 1) null else SpanInfo(cnt - 1, tmp_data)
                data = tmp_data
            }
            result_row.add(data) /////////////
        }
        var i = 0
        for (cell in cells) {
            // find place to set
            while (i < width && result_row[i] != null) {
                i += 1
            if (i >= width)
                throw Exception("invalid cols count in $row_i row")
            }

            val data = cell.text() ///////////////
            result_row[i] = data
            val col_cnt = span2Int(cell, "colspan")
            if (1 > col_cnt && col_cnt > width - 1)
                throw Exception("invalid cols count in $row_i row by col_span($col_cnt) in $i column")

            val row_cnt = span2Int(cell, "rowspan")
            if (1 > row_cnt)
                throw Exception("invalid cols count in $row_i row by row_span($row_cnt) in $i column")

            for (j in 0 until col_cnt) {
                result_row[i] = data
                if (row_cnt > 1)
                    prev_row_info[i] = SpanInfo(row_cnt - 1, data)
                i += 1
            }
        }
        result.add(result_row)
    }

    return result.toMutableList()
}

