using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using EAD2CA2;

namespace EAD2CA2.Data
{
    public class EAD2CA2Context : DbContext
    {
        public EAD2CA2Context (DbContextOptions<EAD2CA2Context> options)
            : base(options)
        {
        }

        public DbSet<EAD2CA2.Sellers> Sellers { get; set; }

        public DbSet<EAD2CA2.Clothes> Clothes { get; set; }
    }
}
