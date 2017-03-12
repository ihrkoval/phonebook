<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 12.03.2017
  Time: 7:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-- Website CSS style -->
    <link rel="stylesheet" type="text/css" href="assets/css/main.css">

    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="row main">
        <div class="center-block">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h1 class="title">PhoneBook</h1>
                <h1>${error}</h1>
                <hr />
            </div>
        </div>
        <div class="main-login main-center">
            <form class="form-horizontal" method="post" action="./register">
                <div class="form-group">
                    <label for="login" class="cols-sm-2 control-label">Ваше логин</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="login" id="login"  placeholder="Введите ваш логин"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="cols-sm-2 control-label">Пароль</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                            <input type="password" class="form-control" name="password" id="password"  placeholder="Enter your Password"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="cols-sm-2 control-label">ФИО</label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="name" id="name"  placeholder="Введите ваше ФИО"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button">Зарегистрироваться</button>
                </div>
                <div class="login-register">
                    <a href="login">Войти</a>
                </div>
            </form>
        </div>
            </div>
    </div>
</div>
<script type="text/javascript" src="assets/js/bootstrap.js"></script>
</body>
</html>
