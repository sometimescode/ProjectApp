<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
</head>
 
<body>
    <a href="<s:url action='catalog' namespace='/'/>">Home</a>
    <a href="<s:url action='logout' namespace='/'/>">Logout</a>
    Hello, <s:property value="#session.firstName"/>
    <a href="<s:url action='profile' namespace='/user'/>">Profile</a>
</body>
</html>