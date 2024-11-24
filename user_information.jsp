<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Information</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            // Function to fetch and display users from the database
            function fetchUsers() {
                console.log("hii");
                $.ajax({
                    url: "UserServlet",
                    type: "GET",
                    success: function (data) {
                        $("#userTable tbody").html(data); // Populate table with user data
                    },
                    error: function () {
                        alert("Failed to fetch users.");
                    }
                });
            }

            // Delete user using AJAX
            function deleteUser(id) {
                $.ajax({
                    url: "UserServlet",
                    type: "POST",
                    data: {action: "delete", id: id},
                    success: function () {
                        fetchUsers(); // Refresh user list
                        alert("User deleted successfully.");
                    },
                    error: function () {
                        alert("Failed to delete user.");
                    }
                });
            }

            // Call fetchUsers on page load
            $(document).ready(function () {
                fetchUsers();

                // Save user using AJAX
                $("#save-btn").click(function () {
                    console.log("save button");
                    const userData = {
                        action: "save",
                        name: $("#name").val(),
                        email: $("#email").val(),
                        phone: $("#phone").val()
                    };
                    console.log(userData);

                    $.ajax({
                        url: "UserServlet",
                        type: "POST",
                        data: userData,
                        success: function () {
                            fetchUsers(); // Refresh user list
                            alert("User saved successfully.");
                            $("#name").val("");
                            $("#email").val("");
                            $("#phone").val("");
                        },
                        error: function () {
                            alert("Failed to save user.");
                        }
                    });
                });
            });
        </script>

        <style>
            #userForm{
                /* border: 1px solid blue; */
                /* background-color: lightblue; */
                display: grid;
                gap: 15px;
                padding: 15px;
                color: #fff;
                grid-template-columns: repeat(3,1fr);
                background: linear-gradient(to left, #353535, #353535);
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body style="background: linear-gradient(to right, #353535, #252525);">
        <div class="container">
            <h2 class="text-white text-center my-4">User Information</h2>

            <!-- Add User Form -->
            <div class="form-container">
                <form id="userForm" class="mb-4" >
                    <div class="mb-3">
                        <label for="name" class="form-label text-white">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label text-white">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label text-white">Phone</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>

                </form>
                <button type="button" id="save-btn" class="btn btn-primary">Save</button>
            </div>

            <!-- Display Users -->
            <table class="table table-striped mt-4" id="userTable">
                <thead>
                    <tr>
                        <th>Sr. No</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Dynamic user data will be loaded here -->
                </tbody>
            </table>
        </div>
    </body>
</html>
