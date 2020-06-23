<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Blank</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <#include "/common/boostrap_css.ftl">
</head>
<body>
<div class="container">
    <div class="row">
            <form action="/source_code/search" method="get" class="form-inline mt-2 col-12">
                <div class="form-group mb-2 col-8" style="padding-left:0;padding-right:0;">
                    <input type="text"
                           class="form-control-plain-text col-12"
                           style="height:38px"
                           placeholder="要检索的内容"
                           aria-label="要检索的内容"
                           aria-describedby="basic-addon2" name="text" value="${text}"
                           autocomplete="off"
                           id="query_content"
                    >
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <button class="btn btn-outline-secondary" type="submit" style="width:90px">搜索</button>
                </div>
            </form>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">项目</th>
                    <th scope="col">文件名</th>
                </tr>
                </thead>
                <tbody>
                <#list inputs as content>
                    <tr>
                        <th scope="row">${content.no}</th>
                        <td>${content.group}/${content.project}</td>
                        <td>${content.fileName}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#include "/common/bootstrap_js.ftl">
<script src="/js/common/bootstrapautocomplete/bootstrap-autocomplete.min.js"></script>
<script src="/js/sourcecode/search.js"></script>

</body>
</html>