
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="/js/dhtmlxchart/dhtmlxchart.js" type="text/javascript"></script>
<link rel="STYLESHEET" type="text/css" href="/js/dhtmlxchart/dhtmlxchart.css" />
<script src="/js/testdata.js"></script>
<script>
window.onload = function() {
    var barChart = new dhtmlXChart({
        view: "bar",
        container: "chartDiv",
        value: "#sales#",
        radius: 0,
        border: true,
        xAxis: {
            template: "'#year#"
        },
        yAxis: {
            start: 0,
            end: 100,
            step: 10,
            template: function(obj) {
                return (obj % 20 ? "": obj);
            }
        }
    });
    barChart.parse(dataset, "json");
};
</script>
 
<div id="chartDiv" style="width:600px;height:250px;margin:20px;border:1px solid #A4BED4"></div>
