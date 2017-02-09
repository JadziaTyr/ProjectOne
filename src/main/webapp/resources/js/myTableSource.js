/**
 * MySource - update screen display here


var rowClicked;

$("table tr td select").change(function()
{
});

$("table tr").change(function()
{
	alert(this.rowIndex);
});
 */

$(document).ready(function(){
    $('#table_id').DataTable();
});