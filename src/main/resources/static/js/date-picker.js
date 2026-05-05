const date_picker = document.getElementById('date-picker');
const dataInicial = document.getElementById('dataInicial');
const dataFinal = document.getElementById('dataFinal');
const datasDefault = dataInicial.value !== "" || dataFinal.value !== ""?
                            [dataInicial.value, dataFinal.value] :
                            null;
const configuracoes = {
                    mode: "range",
                    dateFormat: "Y-m-d",
                    defaultDate: datasDefault,
                    onChange: function(selectedDates, dateStr, instance) {
                        dataInicial.value = instance.formatDate(selectedDates[0], "Y-m-d");
                        dataFinal.value = selectedDates.length > 1 && selectedDates[1].getTime() !== selectedDates[0].getTime() ?
                            instance.formatDate(selectedDates[1], "Y-m-d") :
                            "";
                    }
                }

flatpickr(date_picker, configuracoes);