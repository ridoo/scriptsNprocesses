/**
 * ﻿Copyright (C) 2007 - 2014 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *       • Apache License, version 2.0
 *       • Apache Software License, version 1.0
 *       • GNU Lesser General Public License, version 3
 *       • Mozilla Public License, versions 1.0, 1.1 and 2.0
 *       • Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.wps.server.algorithm.jts;

import org.n52.wps.algorithm.annotation.Algorithm;
import org.n52.wps.algorithm.annotation.ComplexDataInput;
import org.n52.wps.algorithm.annotation.ComplexDataOutput;
import org.n52.wps.algorithm.annotation.Execute;
import org.n52.wps.algorithm.annotation.LiteralDataInput;
import org.n52.wps.io.data.binding.complex.JTSGeometryBinding;
import org.n52.wps.server.AbstractAnnotatedAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.operation.buffer.BufferParameters;

/**
 * This algorithm creates a buffers around a JTS geometry using the build-in buffer-method.
 * @author BenjaminPross(bpross-52n)
 *
 */
@Algorithm(version = "1.0.0", abstrakt="This algorithm creates a buffers around a JTS geometry using the build-in buffer-method.")
public class JTSBufferAlgorithm extends AbstractAnnotatedAlgorithm {

    private static Logger LOGGER = LoggerFactory.getLogger(JTSBufferAlgorithm.class);

    public JTSBufferAlgorithm() {
        super();
    }
    
    private Geometry result;
	private Geometry data;
	private double distance;
	private int quadrantSegments;
	private int endCapStyle;

    @ComplexDataOutput(identifier = "result", binding = JTSGeometryBinding.class)
    public Geometry getResult() {
        return result;
    }

    @ComplexDataInput(identifier = "data", binding = JTSGeometryBinding.class, minOccurs=1)
    public void setData(Geometry data) {
        this.data = data;
    }
    
    @LiteralDataInput(identifier="distance", minOccurs=1)
    public void setDistance(double distance) {
		this.distance = distance;
	}
    
    @LiteralDataInput(identifier="quadrantSegments", defaultValue="" + BufferParameters.DEFAULT_QUADRANT_SEGMENTS, minOccurs=0)
	public void setQuadrantSegments(int quadrantSegments) {
		this.quadrantSegments = quadrantSegments;
	}
    
    @LiteralDataInput(identifier="endCapStyle", abstrakt="CAP_ROUND = 1, CAP_FLAT = 2, CAP_SQUARE = 3", allowedValues={"1","2","3"}, defaultValue = ""+ BufferParameters.CAP_ROUND, minOccurs=0)
	public void setEndCapStyle(int endCapStyle) {
		this.endCapStyle = endCapStyle;
	}

    @Execute
    public void runAlgorithm() {    	
    	result = data.buffer(distance, quadrantSegments, endCapStyle);
    }
}