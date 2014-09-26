/*
 * Copyright 2014 ${name}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.boot.widget

import com.bancvue.boot.resource.AbstractResource
import com.bancvue.rest.params.LongParam
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response
import org.springframework.stereotype.Component

@Component
@Path("/widgets")
public class WidgetResource extends AbstractResource {

	Map<Long, Widget> widgetMap = [:]

	@GET
	public Response getWidgets() {
		return envelopeResponseFactory.createGetManyResponse(widgetMap.values());
	}

	@GET
	@Path("/{id}")
	public Response getWidget(@PathParam("id") LongParam id) {
		Widget widget = widgetMap[id.get()]
		if (widget) {
			return envelopeResponseFactory.createGetResponse(widget);
		} else {
			return envelopeResponseFactory.createNotFoundResponse()
		}
	}

	@POST
	public Response insertWidget(Widget widget) {
		widgetMap[widget.id] = widget
		return envelopeResponseFactory.createPostSuccessResponse(widget);
	}

	@PUT
	@Path("/{id}")
	public Response updateWidget(@PathParam("id") LongParam id, Widget widget) {
		widgetMap[id.get()] = widget
		return envelopeResponseFactory.createPutSuccessResponse(widget);
	}

	@DELETE
	@Path("/{id}")
	public Response deleteFi(@PathParam("id") LongParam id) {
		Widget widget = widgetMap.remove(id.get())
		return envelopeResponseFactory.createDeleteResponse(widget);
	}

}
