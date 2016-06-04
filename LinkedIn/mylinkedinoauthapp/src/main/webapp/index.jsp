<%@page language="java"
        contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <title>LinkedIn OAuth Sample Webapp</title>
    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#linkedInButton").click(makeRequest);
        });

        function makeRequest() {
            // Define properties
            var AUTH_ENDPOINT = "https://www.linkedin.com/uas/oauth2/authorization";
            var RESPONSE_TYPE = "code";
            var CLIENT_ID = "XXXXXXXXXXX";
            var REDIRECT_URI = "http://localhost:8080/mylinkedinoauthapp/callback";
            var SCOPE = "r_basicprofile r_emailaddress";
            var STATE = "123456";

            // Build authorization request endpoint
            // According OAuth 2 specification, all the request parameters should be URL encoded
            var requestEndpoint = AUTH_ENDPOINT + "?" +
                    "response_type=" + encodeURIComponent(RESPONSE_TYPE) + "&" +
                    "client_id=" + encodeURIComponent(CLIENT_ID) + "&" +
                    "redirect_uri=" + encodeURIComponent(REDIRECT_URI) + "&" +
                    "scope=" + encodeURIComponent(SCOPE) + "&" +
                    "state=" + encodeURIComponent(STATE);

            // Send to authorization request endpoint
            window.location.href = requestEndpoint;
        }
    </script>
</head>
<body>

<h1>Register Account</h1>

<table border="0">
    <tr>
        <td>

            <table border="0">
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" id="firstName"/></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastName" id="lastName"/></td>
                </tr>
                <tr>
                    <td>Email Address</td>
                    <td><input type="text" name="emailAddress" id="emailAddress"/></td>
                </tr>
                <tr>
                    <td>Profile Picture URL</td>
                    <td><input type="text" name="pictureUrl" id="pictureUrl"/></td>
                </tr>
                <tr>
                    <td>Public Profile URL</td>
                    <td><input type="text" name="publicProfileUrl" id="publicProfileUrl"/></td>
                </tr>
                <tr>
                    <td>Profile Bio</td>
                    <td><textarea rows="4" cols="50" name="summary" id="summary"></textarea></td>
                </tr>
                <tr>
                    <td>Industry</td>
                    <td><input type="text" name="industry" id="industry"/></td>
                </tr>
                <tr>
                    <td>Industry</td>
                    <td>
                        <button id="register" type="button">Register</button>
                    </td>
                </tr>
            </table>


        </td>
        <td>
            <table border="0">
                <tr>
                    <td><input type="image" src="images/linkedin-button-login.png" id="linkedInButton" width="150"/></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>
</html>