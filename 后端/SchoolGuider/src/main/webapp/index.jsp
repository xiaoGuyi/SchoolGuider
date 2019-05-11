<html>
<body>
<h2>Hello World!</h2>
</body>
</html>

<% String path = request.getContextPath(); %>
<script>
    location.href = "<%=path%>/"
</script>
