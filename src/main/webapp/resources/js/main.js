function disableButtons() {
	PF('updateButton').disable();
	PF('deleteButton').disable();
}

function enableButtons() {
	PF('updateButton').enable();
	PF('deleteButton').enable();
}

function unselectRows() {
	disableButtons();
	PF('dataTable').unselectAllRows();
}
