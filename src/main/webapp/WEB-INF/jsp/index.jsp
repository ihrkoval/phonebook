<%--
  Created by IntelliJ IDEA.
  User: jlab13
  Date: 10.03.2017
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <!-- Website CSS style -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/main.css">
    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

    <title>Contacts</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="col-sm-11">
                    Добавить контакт
                        </div>
                    <div class="col-sm-1">
                        <a href="/logout" class="btn btn-danger" role="button">Logout</a>
                    </div>
                </div>
                <div class="panel-body">
                    <form action="./contact/add" method="post">
                        <div class="panel panel-default">
                            <div class="col-sm-2">
                                <input type="hidden" class="form-control" name="id" value="${id}">
                                <input type="text" class="form-control" name="name" placeholder="Имя" value="${name}">
                                <input type="text" class="form-control" name="surname" placeholder="Фамилия" value="${surname}">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="fathername" placeholder="Отчество" value="${fathername}">
                                <input type="text" class="form-control" name="phone" placeholder="Номер" value="${phonenumber}">
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="homenumber" placeholder="Домашний номер" value="${homenumber}">
                                <input type="text" class="form-control" name="adress" placeholder="Адрес" value="${adress}">
                                </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="email" placeholder="е-майл" value="${email}">
                                <button type="submit" class="btn btn-success" href="/index" value="add">Сохранить</button>
                            </div>



                            </div>
                    </form>
                        </div>



                </div>
                <div class="panel-footer">
                </div>
            </div>
        </div>
</div>
    <div class="row">
        <div class="col-sm-12">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="nameF" placeholder="name"/>
                    </div>
                    <div class="col-sm-2">
                    <input type="text" class="form-control" name="surnameF" placeholder="surname"/>
                        </div>
                    <div class="col-sm-2">
                    <input type="text" class="form-control" name="phoneF" placeholder="phone"/>
                        </div>
                    <button type="submit" class="btn btn-success" onclick="filter()" value="add">Filter</button>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Отчество</th>
                            <th>Номер телефона</th>
                            <th>Домашний номер</th>
                            <th>Адресс</th>
                            <th>е-майл</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        </tbody>
                    </table>

                </div>
                <div class="panel-footer"></div>
            </div>
        </div>

    </div>
</div>
</div>
<script>
    jQuery(function() {
        const tbody = document.getElementById('tbody');

        function getAll(callback) {
            jQuery.ajax({
                url: './contact/all',
                method: 'GET',
                success: callback
            });
        }






        function renderCont(response) {
            while (tbody.firstChild) {
                tbody.removeChild(tbody.firstChild);
            }
            for (let i = 0; i < response.length; i++) {
                const item = response[i];
                var tr = document.createElement("tr");
                var name = document.createElement("td");
                var surname = document.createElement("td");
                var fathername = document.createElement("td");
                var phonenumber = document.createElement("td");
                var homenumber = document.createElement("td");
                var adress = document.createElement("td");
                var email = document.createElement("td");
                var edit = document.createElement("td");
                var del = document.createElement("td");

                var editbtn = document.createElement("button");
                editbtn.className="btn btn-default";
                editbtn.typeName="submit";
                editbtn.setAttribute("onclick","editCont("+item.id+")");
                var span = document.createElement("span");
                span.className="glyphicon glyphicon-pencil";

                var delbtn = document.createElement("button");
                delbtn.setAttribute("onclick","deleteCont("+item.id+")");
                delbtn.className="btn btn-default";
                del.typeName="submit";
                var spand = document.createElement("span");
                spand.className="glyphicon glyphicon-remove";

                name.innerHTML=item.name;
                surname.innerHTML = item.surname;
                fathername.innerHTML = item.fathername;
                phonenumber.innerHTML = item.phonenumber;
                homenumber.innerHTML = item.homenumber;
                adress.innerHTML = item.adress;
                email.innerHTML = item.email;



                tr.appendChild(name);
                tr.appendChild(surname);
                tr.appendChild(fathername);
                tr.appendChild(phonenumber);
                tr.appendChild(homenumber);
                tr.appendChild(adress);
                tr.appendChild(email);
                delbtn.appendChild(spand);
                del.appendChild(delbtn);
                tr.appendChild(del);
                editbtn.appendChild(span);
                edit.appendChild(editbtn);
                tr.appendChild(edit);
                tbody.appendChild(tr);

            }
        }
        filter =  function() {
            var name = document.getElementsByName("nameF")[0].value;
            var surname = document.getElementsByName("surnameF")[0].value;
            var phone = document.getElementsByName("phoneF")[0].value;
            console.log(name);
            var url = "contact/all?name=" + name+"&surname="+surname+"&phone="+phone;

            function sortConts(callback) {
                jQuery.ajax({
                    url: url,
                    method: 'GET',
                    success: callback
                });
            }

            sortConts(renderCont)
        }
        getAll(renderCont);
    });

    function deleteCont(item) {
        jQuery.ajax({
            url: './contact/delete',
            method: 'POST',
            dataType: 'json',
            data: "id="+item,
            statusCode: {
                200: function() {
                    location.href = "/"
                }
            }
        });
    }

    function editCont(item) {
    location.href = "/edit?id="+item;
    }

</script>
</body>
</html>
