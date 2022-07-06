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
    <h1>CATALOG!</h1>
    <s:property value="#session"/>

    <s:if test="#session.role == 'Admin'">
        <a href="<s:url action='dashboard' namespace='/admin'/>">Admin Panel</a>
    </s:if>

    <h2>Fiction Books</h2>
    <s:iterator value="fictionBooks" status="fictionBooksStatus">
      <p>
        <s:if test="cover != null">
          <img src="data:image/jpeg;base64,${cover}"/>
        </s:if>
        ID: <s:property value="dbId"/>
        Title: <s:property value="title"/>
        Authors: <s:property value="authors"/>
      </p>
    </s:iterator>

    <hr>

    <h2>Non-Fiction Books</h2>
    <s:iterator value="nonfictionBooks" status="nonfictionBooksStatus">
      <p>
        <s:if test="cover != null">
          <img src="data:image/jpeg;base64,${cover}"/>
        </s:if>
        ID: <s:property value="dbId"/>
        Title: <s:property value="title"/>
        Authors: <s:property value="authors"/>
      </p>
    </s:iterator>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>