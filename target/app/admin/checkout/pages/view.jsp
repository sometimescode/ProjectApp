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

    <title>Catalog</title>
  </head>
  <body>
    <s:include value="../../pages/adminMenu.jsp"/>

    <h1>CATALOG!</h1>
    <s:property value="#session"/>

    <h2>Pending Checkout Requests</h2>
    <s:iterator value="pendingCheckoutRequests" status="pendingCheckoutRequestsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <s:url action="approveCheckoutRequest" namespace="/admin/checkout" var="approveTag" >
          <s:param name="checkoutRequestId"><s:property value="dbId"/></s:param>
        </s:url>
        <s:url action="rejectCheckoutRequest" namespace="/admin/checkout" var="rejectTag" >
          <s:param name="checkoutRequestId"><s:property value="dbId"/></s:param>
        </s:url>
        <!-- <s:if test="joinBookEntryCover != null">
          <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
        </s:if> -->
        <s:property value="%{#pendingCheckoutRequestsStatus.count}" />
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Requester Id: <s:property value="requesterId"/>
        Status: <s:property value="status"/>
        Request Date: <s:property value="requestDate"/>
        Action: <s:a href="%{approveTag}">Approve</s:a>
        <s:a href="%{rejectTag}">Reject</s:a>
      </p>
    </s:iterator>

    <h2>Approved Checkout Requests</h2>
    <s:iterator value="approvedCheckoutRequests" status="approvedCheckoutRequestsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <s:property value="%{#pendingCheckoutRequestsStatus.count}" />
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Requester Id: <s:property value="requesterId"/>
        Status: <s:property value="status"/>
        Request Date: <s:property value="requestDate"/>
      </p>
    </s:iterator>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>