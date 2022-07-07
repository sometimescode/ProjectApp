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

    <title>Book Information</title>
  </head>
  <body>
    <s:include value="../../pages/adminMenu.jsp" />
    <s:property value="#session"/>
    <h1>Book</h1>

    <a href="<s:url action='addBookCopyRedirect' namespace='/admin/book'/>">Add Copy</a>
    <a href="<s:url action='editBookEntryFormById' namespace='/admin/book'/>">Edit Book Details</a>

    <s:if test="bookEntryBean.cover != null">
        Cover: 
        <img src="data:image/jpeg;base64,${bookEntryBean.cover}"/>
    </s:if>
    ID: <s:property value="bookEntryBean.dbId"/>
    Title: <s:property value="bookEntryBean.title"/>
    Authors: <s:property value="bookEntryBean.authors"/>
    ISBN: <s:property value="bookEntryBean.ISBN"/>
    Page Count: <s:property value="bookEntryBean.pageCount"/>
    Publisher: <s:property value="bookEntryBean.publisher"/>
    Published Date: <s:property value="bookEntryBean.publishedDate"/>
    Genre: <s:property value="bookEntryBean.genre"/>

    <hr>
    <h2>Book Copies</h2>
    <s:iterator value="bookCopies" status="bookCopiesStatus">
      <p>
        ID: <s:property value="dbId"/>
        CurrentCheckoutRecordId: <s:property value="currentCheckoutRecordId"/>
        Checked Out: <s:property value="checkedOut"/>
        Purchase Price: <s:property value="purchasePrice"/>
        Available: <s:property value="available"/>
      </p>
    </s:iterator>
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>