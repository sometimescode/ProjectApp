<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Error</title>
  </head>
  <body>
    <h3>ERROR PAGE</h3>

    <p>There is an error: <s:property value="error" /> </p>

    <p><a href="<s:url action='index' namespace='/' />" >Return to home page</a>.</p>
  </body>
</html>