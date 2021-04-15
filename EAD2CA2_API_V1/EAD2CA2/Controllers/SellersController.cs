﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using EAD2CA2;
using EAD2CA2.Data;

namespace EAD2CA2.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SellersController : ControllerBase
    {
        private readonly EAD2CA2Context _context;

        public SellersController(EAD2CA2Context context)
        {
            _context = context;
        }

        // GET: api/Sellers
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Sellers>>> GetSellers()
        {
            return await _context.Sellers.ToListAsync();
        }

        // GET: api/Sellers/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Sellers>> GetSellers(int id)
        {
            var sellers = await _context.Sellers.FindAsync(id);

            if (sellers == null)
            {
                return NotFound();
            }

            return sellers;
        }

        // PUT: api/Sellers/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutSellers(int id, Sellers sellers)
        {
            if (id != sellers.ID)
            {
                return BadRequest();
            }

            _context.Entry(sellers).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SellersExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Sellers
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Sellers>> PostSellers(Sellers sellers)
        {
            _context.Sellers.Add(sellers);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSellers", new { id = sellers.ID }, sellers);
        }

        // DELETE: api/Sellers/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSellers(int id)
        {
            var sellers = await _context.Sellers.FindAsync(id);
            if (sellers == null)
            {
                return NotFound();
            }

            _context.Sellers.Remove(sellers);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool SellersExists(int id)
        {
            return _context.Sellers.Any(e => e.ID == id);
        }
    }
}
