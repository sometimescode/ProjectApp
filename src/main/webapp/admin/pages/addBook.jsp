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
    
    <h1>Add Book</h1>
    ISBN To Search: <s:property value="queryISBN"></s:property>
    <hr>
    <s:property value="ISBNResponseBean"></s:property>
    <hr>
    Auto-populate form with details from OpenLibrary using ISBN with the search form below or manually input the details.
    <s:property value="bookEntryBean"></s:property>
    <s:form action="searchBookFromOpenLibrary">
      <s:textfield name="queryISBN" label="ISBN"/> 
      <s:submit value="Search"/>
    </s:form>

    Add Book Form
    <s:if test="bookEntryBean.cover != null">
      Cover: 
      <img src="${bookEntryBean.cover}"/>
    </s:if>

    <s:form action="addBookEntry">
      <s:textfield name="bookEntryBean.title" label="Title"/>
      <s:select name="bookEntryBean.authors" label="Author" value="bookEntryBean.authors" list="authorList" multiple="true" size="3" />
      <s:textfield name="bookEntryBean.ISBN" label="ISBN" disabled="true"/> 
      <s:hidden name="bookEntryBean.ISBN"/>
      <s:textfield name="bookEntryBean.pageCount" label="Page Count"/> 
      <s:textfield name="bookEntryBean.publisher" label="Publisher"/> 
      <s:textfield name="bookEntryBean.publishedDate" label="Published Date"/>
      <s:select name="bookEntryBean.genre" label="Genre" emptyOption="true" list="genreList"/>
      <s:hidden name="bookEntryBean.cover"/>
      <s:submit value="Add"/>
    </s:form>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>