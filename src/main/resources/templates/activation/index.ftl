<#assign content>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <h3 class="heading-title">Manual Activation</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        Read Humidity
                    </div>
                    <div class="card-body">
                        <form action="${MANUAL_ACTIVATION}${READ_HUMIDITY}" method="post" class="form-horizontal">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-dot-circle-o"></i> Read humidity
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="row">
                            <div class="col-md-4 offset-4">
                                <i class="fa fa-clock"></i>
                                    <#if model.humidity == 0></#if>
                                    <#if model.humidity == -1>Read Error</#if>
                                    <#if model.humidity gt 0>${model.humidity} %</#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        Read Temperature
                    </div>
                    <div class="card-body">
                        <form action="${MANUAL_ACTIVATION}${READ_TEMPERATURE}" method="post" class="form-horizontal">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-dot-circle-o"></i> Read temperature
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="row">
                            <div class="col-md-4 offset-4">
                                <i class="fa fa-clock"></i>
                                    <#if model.temperature == 0></#if>
                                    <#if model.temperature == -1>Read Error</#if>
                                    <#if model.temperature gt 0>${model.temperature} C</#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        Pump Action
                    </div>
                    <div class="card-body">
                        <form action="${MANUAL_ACTIVATION}${PUMP_ACTION}" method="post" class="form-horizontal">
                            <div class="row form-group">
                                <div class="col-md-4">
                                    <label for="action" class="form-control-sm">Turn:</label>
                                    <select id="action" class="form-control-sm" name="action">
                                        <option value="1" <#if model.action == 1>selected</#if>>On</option>
                                        <option value="0" <#if model.action == 0>selected</#if>>Off</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-dot-circle-o"></i> Send command
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>

    </div>

</#assign>

<#include "../template.ftl" />