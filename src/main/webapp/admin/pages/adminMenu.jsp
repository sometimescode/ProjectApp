<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
</head>
 
<body>
    <a href="<s:url action='catalog' namespace='/'/>">Home</a>
    <a href="<s:url action='dashboard' namespace='/admin'/>">Admin Dashboard</a>
    <a href="<s:url action='logout' namespace='/'/>">Logout</a>
    Hello, <s:property value="#session.firstName"/>
</body>
</html>