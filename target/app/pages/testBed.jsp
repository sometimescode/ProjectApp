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

    <title>Test Bed - Welcome</title>
  </head>
  <body>
    <h1>Welcome to TEST ZONE!</h1>
    <s:property value="select"></s:property>
    <s:form action="testBed">
        <s:select name="select" label="Selecterino" value="%{'Jeeves'}" list="names" multiple="true" size="3" />
        <s:submit/>
    </s:form>

    <s:form action="imgUpload" method="post" enctype="multipart/form-data">
      <s:select name="select" label="Selecterino" value="%{'Jeeves'}" list="names" multiple="true" size="3" />
      <s:file name="upload" label="File"/>
      <s:submit/>
    </s:form>

    <s:property value="base64Cover"/>
    <s:form action="getImg">
      <s:submit/>
    </s:form>
    IMAGE:
    <img src="data:image/jpg;base64,${base64Cover}" alt="images Here"/>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>