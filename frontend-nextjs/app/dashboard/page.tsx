"use client";

import ProductCard from "@/components/ui/product-card";
import React from "react";

export default function Dashboard() {

  type ProductData = {
    id: string;
    name: string;
    description: string,
    price: number;
    category: string;
  }

  const backendURL = process.env.NEXT_PUBLIC_BACKEND_URL;
  const [products, setProducts] = React.useState<ProductData[]>([]);

  React.useEffect(() => {
    // Here I have to make the call to the backend service.
    async function fetchProducts() {
      try {
        const response = await fetch(`${backendURL}/api/product`, {
          method: "GET",
        });

        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }

        const data = await response.json();

        setProducts(data);
      } catch (error: any) {
        console.log("Error fetching products: " + error)
      }
    }

    fetchProducts();
  }, [])

  return (
    <div className="space-y-8">
      <div className="space-y-2">
        <h1 className="text-3xl font-bold">Educational Products</h1>
        <p className="text-muted-foreground">
          Discover notes, projects, and study materials
        </p>
      </div>

      <div
        className="
        grid gap-6
        grid-cols-1
        sm:grid-cols-2
        lg:grid-cols-3
      "
      >
        {products.map((p) => (
          <ProductCard key={p.id} {...p} />
        ))}
      </div>
    </div>
  );
}
