package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.IssueReport
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

interface IssueReportRepository {
    suspend fun insertIssueReport(report: IssueReport): ResultType<Unit, DataError>
}