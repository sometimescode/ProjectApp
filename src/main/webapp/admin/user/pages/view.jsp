<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>User View</title>
  </head>
  <body>
    <s:include value="../../pages/adminMenu.jsp" />

    <h1>Profile</h1>
    <s:property value="#session"/>
    <s:property value="userId"/>
    First Name: <s:property value="userBean.firstName"/>
    Last Name: <s:property value="userBean.lastName"/>
    Email: <s:property value="userBean.email"/>
    Contact Number: <s:property value="userBean.contactNumber"/>

    <h2>Checkout Records</h2>
    <s:iterator value="checkoutRecords" status="checkoutRecordsStatus">
        <p>
            <s:url action="book" namespace="/" var="urlTag" >
                <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
            </s:url>
            <!-- <s:if test="joinBookEntryCover != null">
              <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
            </s:if> -->
            <s:property value="%{#checkoutRecordsStatus.count}" />
            ID: <s:property value="dbId"/>
            Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
            Copy: #<s:property value="bookCopyId"/>
            Checkout Date: <s:property value="checkoutDate"/>
            Expected Return Date: <s:property value="expectedReturnDate"/>
            Actual Return Date: <s:property value="actualReturnDate"/>
            Status: <s:property value="status"/>
            Fine: <s:property value="fine"/>
            Fine Paid ? <s:property value="finePaid"/>
        </p>
    </s:iterator>

    <hr>

    <h2>Checkout Requests</h2>
    <s:iterator value="onlineCheckoutRequests" status="onlineCheckoutRequestsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <!-- <s:if test="joinBookEntryCover != null">
          <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
        </s:if> -->
        <s:property value="%{#onlineCheckoutRequestsStatus.count}" />
        ID: <s:property value="dbId"/>
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Status: <s:property value="status"/>
        Request Date: <s:property value="requestDate"/>
        Status Update Date: <s:property value="statusUpdateDate"/>
      </p>
    </s:iterator>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>