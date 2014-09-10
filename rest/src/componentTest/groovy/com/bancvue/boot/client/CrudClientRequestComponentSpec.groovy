package com.bancvue.boot.client

import com.bancvue.boot.cm.spring.BVConfigContextLoader
import com.bancvue.boot.testsupport.ComponentTestConfig
import com.bancvue.boot.testsupport.ResourceIntegrationTestSupport
import com.bancvue.boot.widget.Widget
import com.bancvue.boot.widget.WidgetResource
import com.bancvue.boot.widget.WidgetServiceConfig
import com.bancvue.rest.client.request.BasicClientRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@IntegrationTest
@WebAppConfiguration
@ContextConfiguration(classes = [WidgetServiceConfig, ComponentTestConfig], loader = BVConfigContextLoader)
class CrudClientRequestComponentSpec extends Specification {

	@Autowired
	private ResourceIntegrationTestSupport resourceSupport
	@Autowired
	WidgetResource resource
	private CrudClientRequest<Widget> crudRequest

	def setup() {
		BasicClientRequest clientRequest = new BasicClientRequest(resourceSupport.resource)
		crudRequest = new CrudClientRequest<>(clientRequest, Widget.class).path("/root/widgets")
	}

	def cleanup() {
		resource.widgetMap.clear()
	}

	def "find"() {
		given:
		resource.widgetMap[1l] = new Widget(1)

		when:
		Widget widget = crudRequest.find(1)

		then:
		widget == new Widget(1)
	}

	def "findMany"() {
		given:
		resource.widgetMap[1l] = new Widget(1)
		resource.widgetMap[2l] = new Widget(2)

		when:
		List<Widget> widgets = crudRequest.findMany()

		then:
		widgets == [new Widget(1), new Widget(2)]
	}

	def "createWithPost"() {
		when:
		crudRequest.createWithPost(new Widget(1))

		then:
		resource.widgetMap[1l] == new Widget(1)
		resource.widgetMap.size() == 1
	}

	def "updateWithPut"() {
		given:
		resource.widgetMap[1l] = new Widget(5)

		when:
		crudRequest.updateWithPut(1, new Widget(1))

		then:
		resource.widgetMap[1l] == new Widget(1)
	}

	def "delete"() {
		given:
		Widget widget = new Widget(1)
		resource.widgetMap[1l] = widget

		when:
		crudRequest.path(widget).delete()

		then:
		resource.widgetMap.isEmpty()
	}

}
