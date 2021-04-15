using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace EAD2CA2
{
    public class Sellers
    {
        [Required]
        [Key]
        public int ID { get; set; }

        [Required]
        public string Name { get; set; }

        public string Location { get; set; }

        public int Rating { get; set; }

        public int ClothesSold { get; set; }
    }

}
