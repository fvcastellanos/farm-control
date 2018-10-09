<#assign content>

    <div class="container-fluid">
        <#--<div class="row">-->
            <#--<div class="col">-->
                <#--<section class="card">-->
                    <#--<div class="card-body text-secondary">.col</div>-->
                <#--</section>-->
            <#--</div>-->
            <#--<div class="col">-->
                <#--<section class="card">-->
                    <#--<div class="card-body text-secondary">.col</div>-->
                <#--</section>-->
            <#--</div>-->
            <#--<div class="col">-->
                <#--<section class="card">-->
                    <#--<div class="card-body text-secondary">.col</div>-->
                <#--</section>-->
            <#--</div>-->
        <#--</div>-->
        <div class="row">
            <div class="col-md-11">
                <h5 class="heading-title">Sensor Readings</h5>
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
                <form action="/" method="post">
                    <div class="col-md-2">
                        <label for="display" class="form-control-label">No. Results</label>

                    </div>
                    <div class="col-md-2">
                        <select id="display" class="form-control" name="display">
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>

    </div>
</#assign>

<#include "../template.ftl" />