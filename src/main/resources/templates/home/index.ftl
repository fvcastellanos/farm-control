<#assign content>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <h3 class="heading-title">Sensor Readings</h3>
                <div class="table-responsive table--no-card m-b-30">
                    <table class="table table-borderless table-striped table-earning">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>date</th>
                            <th class="text-right">temp. th</th>
                            <th class="text-right">temp.</th>
                            <th class="text-right">hum. th</th>
                            <th class="text-right">hum.</th>
                            <th>pump. activated</th>
                            <th>error</th>
                            <th>message</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list model.readEvents as event>
                            <tr>
                                <td>${event.id}</td>
                                <td>${event.created?datetime?string}</td>
                                <td class="text-right">${event.temperatureThreshold}</td>
                                <td class="text-right">${event.temperatureValue}</td>
                                <td class="text-right">${event.humidityThreshold}</td>
                                <td class="text-right">${event.humidityValue}</td>
                                <td>${event.pumpActivated?string('Yes', 'No')}</td>
                                <td>${event.readError?string('Yes', 'No')}</td>
                                <td>${event.message!""}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 offset-md-6">
                <div class="card">
                    <div class="card-header">
                        No. Results
                    </div>
                    <div class="card-body">
                        <form action="/" method="post" class="form-horizontal">
                            <div class="row form-group">
                                <div class="col-md-4">
                                    <select id="display" class="form-control" name="display">
                                        <option value="10" <#if model.displayLimit == 10>selected</#if>>10</option>
                                        <option value="20" <#if model.displayLimit == 20>selected</#if>>20</option>
                                        <option value="50" <#if model.displayLimit == 50>selected</#if>>50</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-dot-circle-o"></i> Update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="au-card m-b-30">
                    <div class="au-card-inner">
                        <h3 class="title-2 m-b-40">Temperature</h3>
                        <canvas id="tempChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="au-card m-b-30">
                    <div class="au-card-inner">
                        <h3 class="title-2 m-b-40">Humidity</h3>
                        <canvas id="humChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="au-card m-b-30">
                    <div class="au-card-inner">
                        <h3 class="title-2 m-b-40">Pump Activations</h3>
                        <canvas id="pumpChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

    </div>
</#assign>

<#assign customJs>
    <script>
        var ctx = document.getElementById("tempChart").getContext('2d');

        var tempChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [
                    <#list model.temperatureReads as temp>
                        '${temp.date?datetime?string}'<#if temp_has_next>,</#if>
                    </#list>
                ],
                datasets: [{
                    label: 'C',
                    data: [
                    <#list model.temperatureReads as temp>
                        ${temp.value}<#if temp_has_next>,</#if>
                    </#list>
                    ],
                    backgroundColor: [
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });

        var ctxh = document.getElementById("humChart").getContext('2d');

        var humChart = new Chart(ctxh, {
            type: 'line',
            data: {
                labels: [
                    <#list model.humidityReads as hum>
                        '${hum.date?datetime?string}'<#if hum_has_next>,</#if>
                    </#list>
                ],
                datasets: [{
                    label: '%',
                    data: [
                    <#list model.humidityReads as hum>
                        ${hum.value}<#if hum_has_next>,</#if>
                    </#list>
                    ],
                    backgroundColor: [
                        'rgba(255, 206, 86, 0.2)'

                    ],
                    borderColor: [
                        'rgba(255, 206, 86, 0.2)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });

        var ctxp = document.getElementById("pumpChart").getContext('2d');

        var pumpChart = new Chart(ctxp, {
            type: 'doughnut',
            data: {
                datasets: [{
                    label: '%',
                    data: [
                        ${model.pumpActivations.totalOn},
                        ${model.pumpActivations.totalOff}
                    ],
                    backgroundColor: [
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(255, 159, 64, 0.2)'

                    ],
                    borderColor: [
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(255, 159, 64, 0.2)'

                    ],
                    borderWidth: 1

                }],
                labels: [
                    'On', 'Off'
                ]
            }
        });

    </script>
</#assign>

<#include "../template.ftl" />