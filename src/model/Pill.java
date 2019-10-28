/*
        Licensed to the Apache Software Foundation (ASF) under one
        or more contributor license agreements.  See the NOTICE file
        distributed with this work for additional information
        regarding copyright ownership.  The ASF licenses this file
        to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
        with the License.  You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,
        software distributed under the License is distributed on an
        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        KIND, either express or implied.  See the License for the
        specific language governing permissions and limitations
        under the License.
 */

package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pill {

	private int id;
    private Date fecha;
    private String correoPaciente, dia, pastilla, dosis;

	public Pill(int id, String correoPaciente, Date fecha, String dia, String pastilla, String dosis) {
		this.id = id;
		this.correoPaciente = correoPaciente;
		this.fecha = fecha;
		this.dia = dia;
		this.pastilla = pastilla;
		this.dosis = dosis;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCorreoPaciente() {
		return correoPaciente;
	}

	public void setCorreoPaciente(String correoPaciente) {
		this.correoPaciente = correoPaciente;
	}

	public String getPastilla() {
		return pastilla;
	}

	public void setPastilla(String pastilla) {
		this.pastilla = pastilla;
	}

	public String getDosis() {
		return dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}
}
