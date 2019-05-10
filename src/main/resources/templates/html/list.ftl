<html>
    <head>
        <title>秒杀列表页</title>
        <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <h2  align="center">秒杀列表</h2>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>
                            名称
                        </th>
                        <th>
                            库存
                        </th>
                        <th>
                            开始时间
                        </th>
                        <th>
                            结束时间
                        </th>
                        <th>
                            创建时间
                        </th>
                        <th>
                            详情页
                        </th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list list as seckill>
                    <tr>

                        <td>
                            ${seckill.name}
                        </td>
                        <td>
                            ${seckill.number}
                        </td>
                        <td>
                            ${seckill.startTime}
                        </td>
                        <td>
                            ${seckill.endTime}
                        </td>
                        <td>
                            ${seckill.createTime}
                        </td>
                        <td>
                            <a href="/seckill/${seckill.seckillId?c}/detail" class="btn btn-default btn-primary">详情</a>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </body>

    <script src="https://cdn.bootcss.com/jquery/2.0.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/js/bootstrap.min.js"></script>


</html>




