import React from 'react';
import ProductCard from '../components/ProductCard';

const ProductSection = () => {
  const products = [
    {
      id: 1,
      title: "Smart Notebook",
      description: "Organize your notes efficiently and boost productivity.",
      price: 29.99,
      badge: "Best Seller",
      image: null
    },
    {
      id: 2,
      title: "AI Learning Tool",
      description: "Improve learning speed and retention with AI assistance.",
      price: 49.99,
      badge: "New",
      image: null
    },
    {
      id: 3,
      title: "Project Templates",
      description: "Ready-to-use templates for your assignments and projects.",
      price: 19.99,
      badge: "Hot",
      image: null
    },
    {
      id: 4,
      title: "E-Library Access",
      description: "Unlimited access to curated digital study materials.",
      price: 39.99,
      badge: "Popular",
      image: null
    }
  ];

  return (
    <section className="bg-[var(--foreground-color)] py-20">
      <div className="max-w-7xl mx-auto px-6 text-center">
        <h2 className="text-5xl md:text-6xl font-extrabold text-[var(--primary-text-color)] mb-4">
          Our Products
        </h2>
        <p className="text-xl md:text-2xl text-[var(--primary-text-color)] font-medium mb-16">
          Bringing you the best, so you can focus on what matters.
        </p>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-10">
          {products.map(product => (
            <ProductCard
              key={product.id}
              image={product.image}
              title={product.title}
              description={product.description}
              price={product.price}
              badge={product.badge}
            />
          ))}
        </div>
        <button className="mt-20 bg-[var(--background-color)] backdrop-blur lg:w-[30vw] py-3 rounded-full font-semibold hover:scale-105 transition duration-300 ease-in-out cursor-pointer active:scale-100">View More Products</button>
      </div>
    </section>
  );
};

export default ProductSection;