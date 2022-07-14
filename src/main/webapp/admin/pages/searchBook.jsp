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
    <s:include value="adminMenu.jsp" />
    
    <h1>Search Book</h1>
    <s:form action="searchBookFromDB">
      <s:textfield name="queryTitle" label="Title"/> 
      <s:submit value="Search"/>
    </s:form>

    <s:iterator value="resultBooks" status="resultBooksStatus">
        <p>
          <s:url action="getBookEntryByISBN" namespace="/admin/book" var="urlTag" >
            <s:param name="ISBN"><s:property value="ISBN"/></s:param>
          </s:url>
          <s:if test="cover != null">
            <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${cover}"/></s:a>
          </s:if>
          ISBN: <s:property value="ISBN"/>
          Title: <s:property value="title"/>
          Authors: <s:property value="authors"/>
        </p>
    </s:iterator>
    
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>