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

    <!-- Datatables CSS -->
    <link href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css" rel="stylesheet">

    <title>Catalog</title>
  </head>
  <body>

    <s:include value="adminMenu.jsp"/>

    <h1>Checkout Records</h1>
    <s:property value="#session"/>

    <h2>Expected Today</h2>
    <s:iterator value="expectedCheckins" status="expectedCheckinsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <s:url action="userView" namespace="/admin/user" var="userTag" >
          <s:param name="userId"><s:property value="borrowerId"/></s:param>
        </s:url>
        <s:url action="checkinCheckoutRecord" namespace="/admin/checkin" var="checkinTag" >
          <s:param name="checkoutRecordId"><s:property value="dbId"/></s:param>
          <s:param name="bookCopyId"><s:property value="bookCopyId"/></s:param>
        </s:url>
        <s:if test="joinBookEntryCover != null">
          <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
        </s:if>
        <s:property value="%{#expectedCheckinsStatus.count}" />
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Copy: #<s:property value="bookCopyId"/>
        Borrower: <s:a href="%{userTag}"><s:property value="joinAccountLastName"/>, <s:property value="joinAccountFirstName"/></s:a>
        Action: <s:a href="%{checkinTag}">Check In</s:a>
      </p>
    </s:iterator>

    <h2>Late Expected Checkins</h2>
    <s:iterator value="lateExpectedCheckins" status="lateExpectedCheckinsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <s:url action="userView" namespace="/admin/user" var="userTag" >
          <s:param name="userId"><s:property value="borrowerId"/></s:param>
        </s:url>
        <s:url action="checkinCheckoutRecord" namespace="/admin/checkin" var="checkinTag" >
          <s:param name="checkoutRecordId"><s:property value="dbId"/></s:param>
          <s:param name="bookCopyId"><s:property value="bookCopyId"/></s:param>
        </s:url>
        <s:if test="joinBookEntryCover != null">
          <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
        </s:if>
        <s:property value="%{#lateExpectedCheckinsStatus.count}" />
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Copy: #<s:property value="bookCopyId"/>
        Borrower: <s:a href="%{userTag}"><s:property value="joinAccountLastName"/>, <s:property value="joinAccountFirstName"/></s:a>
        Expected Return Date: <s:property value="expectedReturnDate"/>
        Action: <s:a href="%{checkinTag}">Check In</s:a>
      </p>
    </s:iterator>

    <h2>Checkins Today</h2>
    <s:iterator value="checkins" status="checkinsStatus">
      <p>
        <s:url action="book" namespace="/" var="urlTag" >
            <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
        </s:url>
        <s:url action="userView" namespace="/admin/user" var="userTag" >
          <s:param name="userId"><s:property value="borrowerId"/></s:param>
        </s:url>
        <s:url action="checkinCheckoutRecord" namespace="/admin/checkin" var="checkinTag" >
          <s:param name="checkoutRecordId"><s:property value="dbId"/></s:param>
          <s:param name="bookCopyId"><s:property value="bookCopyId"/></s:param>
        </s:url>
        <s:if test="joinBookEntryCover != null">
          <s:a href="%{urlTag}"><img src="data:image/jpeg;base64,${joinBookEntryCover}"/></s:a>
        </s:if>
        <s:property value="%{#lateExpectedCheckinsStatus.count}" />
        Title: <s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a>
        Copy: #<s:property value="bookCopyId"/>
        Borrower: <s:a href="%{userTag}"><s:property value="joinAccountLastName"/>, <s:property value="joinAccountFirstName"/></s:a>
      </p>
    </s:iterator>
    
    <hr>
    Table Test All Checkout Records
    <table id="example" class="cell-border" style="width:100%">
      <thead>
          <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Copy #</th>
              <th>Borrower</th>
              <th>Checkout Date</th>
              <th>Expected Return Date</th>
              <th>Actual Return Date</th>
              <th>Status</th>
              <th>Fine</th>
              <th>Fine Paid?</th>
          </tr>
      </thead>
      <tbody>
        <s:iterator value="checkoutRecords" status="checkoutRecords">
            <s:url action="book" namespace="/" var="urlTag" >
                <s:param name="ISBN"><s:property value="joinBookEntryISBN"/></s:param>
            </s:url>
            <s:url action="userView" namespace="/admin/user" var="userTag" >
              <s:param name="userId"><s:property value="borrowerId"/></s:param>
            </s:url>
            <tr>
              <td><s:property value="dbId"/></td>
              <th><s:a href="%{urlTag}"><s:property value="joinBookEntryTitle"/></s:a></th>
              <th>#<s:property value="bookCopyId"/></th>
              <th><s:a href="%{userTag}"><s:property value="joinAccountLastName"/>, <s:property value="joinAccountFirstName"/></s:a></th>
              <th><s:property value="checkoutDate"/></th>
              <th><s:property value="expectedReturnDate"/></th>
              <th><s:property value="actualReturnDate"/></th>
              <th><s:property value="status"/></th>
              <th><s:property value="fine"/></th>
              <th><s:property value="finePaid"/></th>
            </tr>
        </s:iterator>
      </tbody>
      <tfoot>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Copy #</th>
            <th>Borrower</th>
            <th>Checkout Date</th>
            <th>Expected Return Date</th>
            <th>Actual Return Date</th>
            <th>Status</th>
            <th>Fine</th>
            <th>Fine Paid</th>
          </tr>
      </tfoot>
  </table>


    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <!-- Jquery -->
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>

    <!-- Datatables -->
    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>

    <script>
      $(document).ready(function () {
          $('#example').DataTable();
      });
  </script>
  </body>
</html>