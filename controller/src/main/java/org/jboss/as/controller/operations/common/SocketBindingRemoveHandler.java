/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.controller.operations.common;


import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;

import java.util.Locale;

import org.jboss.as.controller.Cancellable;
import org.jboss.as.controller.ModelAddOperationHandler;
import org.jboss.as.controller.NewOperationContext;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ResultHandler;
import org.jboss.as.controller.descriptions.DescriptionProvider;
import org.jboss.as.controller.descriptions.common.SocketBindingGroupDescription;
import org.jboss.dmr.ModelNode;

/**
 * Handler for the socket-binding resource's remove operation.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 */
public class SocketBindingRemoveHandler implements ModelAddOperationHandler, DescriptionProvider {

    public static final String OPERATION_NAME = REMOVE;

    public static final SocketBindingRemoveHandler INSTANCE = new SocketBindingRemoveHandler();

    /**
     * Create the SocketBindingRemoveHandler
     */
    protected SocketBindingRemoveHandler() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cancellable execute(NewOperationContext context, ModelNode operation, ResultHandler resultHandler) {
        try {
            PathAddress address = PathAddress.pathAddress(operation.require(OP_ADDR));
            String name = address.getLastElement().getValue();
            ModelNode model = context.getSubModel();
            ModelNode compensating = SocketBindingAddHandler.getOperation(operation.get(OP_ADDR), model);
            uninstallSocketBinding(name, model, context, resultHandler, compensating);
        }
        catch (Exception e) {
            resultHandler.handleFailed(new ModelNode().set(e.getLocalizedMessage()));
        }
        return Cancellable.NULL;
    }

    @Override
    public ModelNode getModelDescription(Locale locale) {
        return SocketBindingGroupDescription.getSocketBindingRemoveOperation(locale);
    }

    protected void uninstallSocketBinding(String name, ModelNode model, NewOperationContext context, ResultHandler resultHandler, ModelNode compensatingOp) {
        resultHandler.handleResultComplete(compensatingOp);
    }

}