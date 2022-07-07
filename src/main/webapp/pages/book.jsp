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
    <s:include value="menu.jsp" />
    <s:property value="submittedPendingRequest"/>
    <s:property value="#session"/>
    <h1>Book</h1>
    <s:property value="ISBN"/>
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
    Available Copies: <s:property value="availableCopies"/>
    
    <s:if test="availableCopies > 0">
      <p>For online, you must submit a checkout request the day before the checkout date. The librarian will update you on the status of your request by the end of the day. When approved, it is reserved for you, and you can pick it up from the counter the next day.
      If you need the book today, please visit the library and checkout from the counter.
      </p>
      <s:if test="submittedPendingRequest == true">
        Request Submitted
      </s:if>
      <s:else>
        <s:form action="requestCheckout">
          <s:submit value="Request Checkout"/>
        </s:form>
      </s:else>

    </s:if>
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>