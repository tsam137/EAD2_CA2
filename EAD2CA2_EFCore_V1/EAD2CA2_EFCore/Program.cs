using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace EAD2CA2_EFCore
{
    public class Seller
    {
        public int ID { get; set; }                 // PK and identity
        public string Name { get; set; }            // null
        public string Location { get; set; }
        public int Rating { get; set; }
        public int ClothesSold { get; set; }
        // navigation property to modules that Lecturer teaches, virtual => lazy loading  
        public virtual ICollection<Clothe> Clothes { get; set; }
    }

    public class Clothe
    {
        public int ID { get; set; }                             // PK and identity
        public string Name { get; set; }                        // null
        public double Price { get; set; }                        // not null, use int? for null
        public string brandName { get; set; }
        public string size { get; set; }
        public string colour { get; set; }
        // foreign key property, null, follows convention for naming
        public int? SellerID { get; set; }
        // update relationship through this property, not through navigation property
        // int would not allow null for LecturerID                 

        // navigation property to Lecturer for this module
        public virtual Seller Seller { get; set; }           // virtual enables "lazy loading" 
    }

    // context class
    public class ShopContext : DbContext
    {
        // localDB connection string
        // c:\users\gary\ShopDB1.mdf
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(@"Server=(localdb)\mssqllocaldb;Database=ShopDB2;Trusted_Connection=True;");
        }

        public DbSet<Seller> Sellers { get; set; }
        public DbSet<Clothe> Clothes { get; set; }
    }


    class ShopRepository
    {

        // print lecturers, their ids, names, and the names of modules they teach 
        public void DoSellerQuery()
        {
            using ShopContext db = new ShopContext();

            var sellers = db.Sellers.OrderBy(s => s.ID).Select(s => new { Id = s.ID, Name = s.Name, Location = s.Location, Rating = s.Rating, ClothesSold = s.ClothesSold, Clothe = s.Clothes });
            Console.WriteLine(sellers.ToQueryString());

            Console.WriteLine("\nSeller:");
            foreach (var seller in sellers)
            {
                Console.WriteLine("id: " + seller.Id + " name: " + seller.Name);
                Console.WriteLine("sells: ");

                // Modules is a navigation propery of type ICollection<Module>
                var SellerClothes = seller.Clothe;
                foreach (var sellerClothe in SellerClothes)
                {
                    Console.WriteLine(sellerClothe.Name);
                }
            }
        }

        // prints the ID and name of each module and the name of the lecturer who teaches it
        public void DoClotheQuery()
        {
            using ShopContext db = new ShopContext();

            // select all modules, ordered by module name
            var clothes = db.Clothes.OrderBy(clothe => clothe.ID);       // load

            Console.WriteLine("\nClothes:");
            foreach (var clothe in clothes)
            {
                Console.WriteLine("id: " + clothe.ID + " name: " + clothe.Name + " ");

                if (clothe.Seller != null)
                {
                    // Lecturer is a navigation property of type Lecturer
                    Console.WriteLine(" solded by: " + clothe.Seller.Name);
                }
            }
        }

        // add a lecturer, modules being taught left null for moment
        public void AddSeller(Seller seller)
        {
            using ShopContext db = new ShopContext();

            try
            {
                // add and save
                db.Sellers.Add(seller);
                db.SaveChanges();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        // add a module, contains lecturerID
        public void AddClothe(Clothe clothe)
        {
            using ShopContext db = new ShopContext();

            try
            {
                // add and save
                db.Clothes.Add(clothe);
                db.SaveChanges();
                // navigation properties updated on both sides
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }


        static class Program
        {
            static void Main()
            {

                ShopRepository repository = new ShopRepository();

                Seller aoc = new Seller() { Name = "Aaron", Location = "Naas", Rating = 4, ClothesSold = 40 };
                repository.AddSeller(aoc);         // ID now assigned

                // teaches 2 modules
                Clothe jeans = new Clothe() { Name = "Jeans", Price = 45, brandName = "Tommy Hilfiger", size = "M", colour = "navy", SellerID = aoc.ID };
                Clothe tracksuit = new Clothe() { Name = "Tracksuits", Price = 30, brandName = "nike", size = "S", colour = "grey", SellerID = aoc.ID };

                Clothe Shoes = new Clothe() { Name = "Shoes", Price = 80, brandName = "Converse", size = "7", colour = "white" };       // null for LecturerID

                repository.AddClothe(jeans);
                repository.AddClothe(tracksuit);
                repository.AddClothe(Shoes);

                repository.DoSellerQuery();
                repository.DoClotheQuery();

                Console.ReadLine();
            }
        }
    }
}
