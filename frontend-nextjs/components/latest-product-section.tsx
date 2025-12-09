"use client";

import ProductCard from "./ui/product-card";

const dummyProducts = [
    {
        id: "#1",
        name: "Python Notes",
        description:
            "High-level and beginner-friendly Python notes covering fundamentals to advanced concepts.",
        price: 199,
        category: "NOTES",
    },
    {
        id: "#2",
        name: "Edukart Project",
        description:
            "Microservices-based ecommerce platform for selling and purchasing educational digital products.",
        price: 499,
        category: "PROJECT",
    },
    {
        id: "#3",
        name: "AI in Python Research Paper",
        description:
            "Research paper exploring Python's impact on modern AI systems and machine learning workflows.",
        price: 149,
        category: "RESEARCH PAPER",
    },
];

export default function LatestProductSection() {
    return (
        <section className="pb-10">
            <div className="mx-auto max-w-6xl space-y-8 px-6 md:space-y-16">
                <div className="relative z-10 mx-auto max-w-xl space-y-6 text-center md:space-y-12">
                    <h2 className="text-4xl font-medium lg:text-5xl">Our Latest Resources</h2>
                    <p>Handpicked content to support your learning journey.</p>
                </div>

                <div className="
          grid gap-10
          grid-cols-[repeat(auto-fit,minmax(260px,1fr))]
        ">
                    {dummyProducts.map(({ id, name, description, category, price }, index) => (
                        <ProductCard
                            key={id}
                            id={id}
                            name={name}
                            description={description}
                            category={category}
                            price={price}
                        />
                    ))}
                </div>

            </div>
        </section>
    );
}
