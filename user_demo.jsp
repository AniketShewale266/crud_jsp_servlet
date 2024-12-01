<%-- 
    Document   : user-demo
    Created on : 1 Dec, 2024, 11:36:37 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Data Fetching</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .container{
                max-width: 1000px;
                /*border: 1px solid red;*/
                margin: 0 auto;
            }
            input{
                width: 50%;
                outline: none;
                border: 1px solid #353535;
                margin: 10px;
                padding: 5px 8px;
            }
            table{
                width: 100%;
            }
            .myData{
                text-align: center;
            }
            .myBtn{
                padding: 8px 15px;
                border: none;
                border-radius: 5px;
                background-color: #353535;
                color: #fff;
                cursor: pointer;
                float: right;
                margin-top: 10px;
            }
            .checkbox{
                width: 18px;
                cursor: pointer;
                height: 18px;
            }

        </style>


    </head>
    <body>
        <div class="container">
            <input type="text" id="my-search" onkeyup="myFunction()"/>

            <button class="myBtn" id="submitButton">Submit</button>
            <table border="1" id="userTable">
                <thead>
                    <tr>
                        <th>Select</th>
                        <th>PinstID</th>
                    </tr>
                </thead>
                <tbody id="userTableBody">
                    <!--                <tr>
                                        <td class="myData"><input type="checkbox" class="checkbox"/></td>
                                        <td class="myData">SRO-0000000021-ANT</td>
                                        
                                    </tr>-->
                </tbody>
            </table>

        </div>

        <script>

            function fetchUsers() {
                $.ajax({
                    url: "UserDemo",
                    type: "GET",
                    success: function (data) {
                        $("#userTable tbody").html(data); // Populate table
                    },
                    error: function () {
                        alert("Failed to fetch users.");
                    }
                });
            }
            function submitSelectedRows() {
                const selectedIds = [];

                // Collect all checked rows
                $("#userTableBody input:checked").each(function () {
                    const pinstID = $(this).closest("tr").find("td:nth-child(2)").text();
                    selectedIds.push(pinstID.trim());
                });

                if (selectedIds.length === 0) {
                    alert("No rows selected!");
                    return;
                }

                // Send selected IDs to the backend for update
                $.ajax({
                    url: "UserDemo", // Your servlet for updating rows
                    type: "POST",
                    data: {selectedIds: selectedIds.join(",")}, // Send IDs as a comma-separated string
                    success: function (response) {
                        alert(response); // Display the server response
                        fetchUsers(); // Refresh table data after update
                    },
                    error: function () {
                        alert("Failed to update selected rows.");
                    }
                });
            }


            $(document).ready(function () {
                fetchUsers();
                $("#submitButton").on("click", submitSelectedRows);
            });

            function myFunction() {
                // Get the value of the search input
                const filter = document.getElementById("my-search").value.toUpperCase();
                const table = document.getElementById("userTable");
                const rows = table.getElementsByTagName("tr");

                // Loop through all table rows (except the first, which contains headers)
                for (let i = 1; i < rows.length; i++) {
                    const cell = rows[i].getElementsByTagName("td")[1]; // Get the PinstID column
                    if (cell) {
                        const txtValue = cell.textContent || cell.innerText;
                        // Show the row if it matches the filter, hide it otherwise
                        rows[i].style.display = txtValue.toUpperCase().includes(filter) ? "" : "none";
                    }
                }
            }

        </script>
    </body>
</html>
