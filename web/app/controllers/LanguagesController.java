package controllers;

import com.picsauditing.model.Language;
import com.picsauditing.web.annotations.ExceptionToFault;
import com.picsauditing.web.models.ServiceFault;
import com.wordnik.swagger.annotations.*;
import models.LanguageModel;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LanguageService;

import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@org.springframework.stereotype.Controller
@Api(
		value = "/languages",
		description = "Languages",
		consumes = "application/json",
		produces = "application/json")
public class LanguagesController extends Controller {

	@Autowired
	private LanguageService languageService;

	@ApiOperation(
			nickname = "languages",
			value = "Map of all languages supported by PICS",
			notes = "Returns a map of all supported languages by PICS\n Env values are - localhost,stable,beta,alpha,config,qa",
			response = Language.class,
			responseContainer = "Map",
			tags = "Language",
			httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Caller provided incorrect parameters", response = ServiceFault.class),
			@ApiResponse(code = 401, message = "Caller is not authorized to view these items", response = ServiceFault.class),
			@ApiResponse(code = 500, message = "An internal error occurred on the server", response = ServiceFault.class),
	})
	@ExceptionToFault
	public Result getLanguages() {

		Map<String, String> languages = languageService.findAllLanguages(Play.application().configuration()
				.getString("pics.env"));
		List<LanguageModel> resultModels = new ArrayList<>();

		if (MapUtils.isNotEmpty(languages)) {
			for (String key : languages.keySet()) {
				LanguageModel model = new LanguageModel(key, languages.get(key));
				resultModels.add(model);
			}
		}

		return ok(Json.toJson(resultModels));
	}

	@ApiOperation(
			nickname = "language",
			value = "Specific Language",
			notes = "Returns a list of supported languages by language name\n Env values are - localhost,stable,beta,alpha,config,qa",
			response = Language.class,
			responseContainer = "List",
			tags = "Language",
			httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Caller provided incorrect parameters", response = ServiceFault.class),
			@ApiResponse(code = 401, message = "Caller is not authorized to view these items", response = ServiceFault.class),
			@ApiResponse(code = 500, message = "An internal error occurred on the server", response = ServiceFault.class),
	})
	@ExceptionToFault
	public Result getLanguage(@ApiParam(value = "language", required = true) @PathParam("language") String language) {
		return ok(Json.toJson(languageService.findLanguageDialects(language, Play.application().configuration()
				.getString("pics.env"))));
	}

	@ApiOperation(
			nickname = "healthCheck",
			value = "Service Health Check",
			notes = "Returns Health of Service ",
			tags = "Health",
			httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Caller provided incorrect parameters", response = ServiceFault.class),
			@ApiResponse(code = 401, message = "Caller is not authorized to view these items", response = ServiceFault.class),
			@ApiResponse(code = 500, message = "An internal error occurred on the server", response = ServiceFault.class),
	})
	@ExceptionToFault
	public Result healthCheck() {
		languageService.findLanguage(Locale.US);
		return ok("ok");
	}
}