<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.JSONObject" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LinkedIn OAuth Sample Webapp</title>
</head>
<body>


<%

    // LinkedIn sends the user profile information in JSON format
    String profile_info = (String) request.getAttribute("profile_info");

    JSONParser parser = new JSONParser();

    Object obj = parser.parse(profile_info);

    JSONObject jsonobj = (JSONObject) obj;

    // Accessing profile attributes from the json object

    String pictureUrl = jsonobj.get("pictureUrl").toString();

    String emailAddress = jsonobj.get("emailAddress").toString();

    String firstName = jsonobj.get("firstName").toString();

    String lastName = jsonobj.get("lastName").toString();

    String publicProfileUrl = jsonobj.get("publicProfileUrl").toString();

    String summary = jsonobj.get("summary").toString();

    String industry = jsonobj.get("industry").toString();
%>


<h1>Register Account</h1>

<table border="0">
    <tr>
        <td>First Name</td>
        <td><input type="text" name="firstName" id="firstName" size="100" value="<%=firstName%>"/></td>
    </tr>
    <tr>
        <td>Last Name</td>
        <td><input type="text" name="lastName" id="lastName" size="100" value="<%=lastName%>"/></td>
    </tr>
    <tr>
        <td>Email Address</td>
        <td><input type=" text" name="emailAddress" id="emailAddress" size="100" value="<%=emailAddress%>"/>
        </td>
    </tr>
    <tr>
        <td>Profile Picture URL</td>
        <td><img height="100" width="100" src="<%=pictureUrl%>"></td>
    </tr>
    <tr>
        <td>Public Profile URL</td>
        <td><input type="text" name="publicProfileUrl" id="publicProfileUrl" size="100" value="<%=publicProfileUrl%>"/></td>
    </tr>
    <tr>
        <td>Profile Bio</td>
        <td><textarea rows="8" cols="100" name="summary" id="summary"><%=summary%></textarea></td>
    </tr>
    <tr>
        <td>Industry</td>
        <td><input type="text" name="industry" id="industry" size="100" value="<%=industry%>"/></td>
    </tr>
        <tr>
        <td></td>
        <td><input type="button" value="Register"/> <td/>
        </td>
    </tr>


</table>

</body>
</html>
