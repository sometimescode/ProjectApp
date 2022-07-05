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
    <h1>Manage Books</h1>
    Add Book
    ISBN To Search: <s:property value="queryISBN"></s:property>
    <hr>
    <s:property value="ISBNResponseBean"></s:property>
    <hr>
    <s:property value="bookEntryBean"></s:property>
    <s:form action="searchBook">
      <s:textfield name="queryISBN" label="ISBN"/> 
      <s:submit value="Search"/>
    </s:form>
    
    <s:form action="addBook">
      <s:textfield name="bookEntryBean.title" label="Title"/>
      <s:select name="bookEntryBean.authors" label="Author" value="bookEntryBean.authors" list="authorList" multiple="true" size="3" />
      <s:textfield name="bookEntryBean.ISBN" label="ISBN"/> 
      <s:textfield name="bookEntryBean.pageCount" label="Page Count"/> 
      <s:textfield name="bookEntryBean.publisher" label="Publisher"/> 
      <s:textfield name="bookEntryBean.publishedDate" label="Published Date"/> 
      <s:textfield name="bookEntryBean.cover" label="Cover"/> 
      <s:submit value="Search"/>
    </s:form>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>