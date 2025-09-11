package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.remote.dto.IssueReportDto
import com.simbiri.myquiz.data.util.toFormattedDateTime
import com.simbiri.myquiz.domain.model.IssueReport

fun IssueReport.toIssueReportDto() = IssueReportDto(
    questionId = questionId,
    issueType= issueType,
    additionalComment= additionalComment,
    userEmail= userEmail,
    timeStamp = timeStampMillis.toFormattedDateTime()
)