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

    <title>Registration</title>
  </head>
  <body>
    <div class="container">
        <h1>Registration Page!</h1>
        <s:property value="test"></s:property>
        HERE'S USERBEAN!<s:property value="userBean"/>
        <s:form action="register">
            <s:textfield name="test" label="test"/>
            <s:textfield name="userBean.username" label="Username"/>
            <s:textfield name="userBean.password" label="Password"/>
            <s:textfield name="userBean.firstName" label="First Name"/>
            <s:textfield name="userBean.lastName" label="Last Name"/>
            <s:textfield name="userBean.email" label="Email"/>
            <s:textfield name="userBean.contactNumber" label="Contact Number"/>
            <s:submit/>
        </s:form>
    </div>
    
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>