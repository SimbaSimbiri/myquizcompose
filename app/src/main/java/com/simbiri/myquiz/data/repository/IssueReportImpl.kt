package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toIssueReportDto
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.domain.model.IssueReport
import com.simbiri.myquiz.domain.repository.IssueReportRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

class IssueReportImpl(
    private val remoteDataSource: RemoteDataSource
): IssueReportRepository {
    override suspend fun insertIssueReport(
        report: IssueReport
    ): ResultType<Unit, DataError> {
        return remoteDataSource.insertIssueReport(report.toIssueReportDto())

    }
}