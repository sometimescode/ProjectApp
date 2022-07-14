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

    <title>Books</title>
  </head>
  <body>
    <s:include value="../../pages/adminMenu.jsp"/>
    
    <h1>Add Checkout Record</h1>
    <s:property value="#session"></s:property>
    Add Checkout Record Form

    RecordBean
    <s:property value="checkoutRecordBean"></s:property>


    <s:form action="addCheckoutRecord">
        <s:select name="checkoutRecordBean.bookCopyId" label="Select Available Book Copy to Checkout" value="availableBookCopies.dbId" list="bookCopySelectList"/>
        <s:textfield name="checkoutRecordBean.bookEntryId" hidden="true"/>
        <s:textfield name="checkoutRecordBean.borrowerId" hidden="true"/>
        <s:textfield name="checkoutRecordBean.onlineCheckoutRequestId" hidden="true"/>
        <s:submit value="Add"/>
    </s:form>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>