package com.fit.controller.act.editor;

import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * StencilsetRestResource
 */
@RestController
public class StencilsetRestResource {
  
  @RequestMapping(value="/editor/stencilset", method = RequestMethod.GET)
  public @ResponseBody String getStencilset() {
    InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
    try {
      return IOUtils.toString(stencilsetStream, "utf-8");
    } catch (Exception e) {
      throw new ActivitiException("Error while loading stencil set", e);
    }
  }
}
