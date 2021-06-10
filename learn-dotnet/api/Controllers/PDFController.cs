using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace api.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class PDFController
    {
        private readonly Dictionary<int, string> _documents = new Dictionary<int, string>{
            // Documents DB
        };
        private readonly ILogger<PDFController> _logger;

        public PDFController(ILogger<PDFController> logger)
        {
            _logger = logger;
        }

        [HttpGet()]
        public async Task<IActionResult> Get(int id)
        {
            if (_documents.ContainsKey(id))
            {
                try
                {
                    var fileContent = await File.ReadAllBytesAsync(_documents[id]);
                    
                    return new FileContentResult(fileContent, "application/pdf");
                }
                catch(Exception ex) 
                {
                    _logger.LogError(ex, ex.Message, new object[]{});
                    return new StatusCodeResult(500);
                }
            }
            
            return new NotFoundResult();
        }
    }
}