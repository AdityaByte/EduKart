"use client";

import ProductCard from "./ui/product-card";

const dummyProducts = [
    {
        tag: "new",
        name: "Python Notes",
        desc: "Python is a high-level, interpreted, and general-purpose programming language known for its readability and simplicity.",
    },
    {
        tag: "new",
        name: "Python Notes",
        desc: "Python is a high-level, interpreted, and general-purpose programming language known for its readability and simplicity.",
    },
    {
        tag: "new",
        name: "Python Notes",
        desc: "Python is a high-level, interpreted, and general-purpose programming language known for its readability and simplicity.",
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
                    {dummyProducts.map(({ tag, name, desc }, index) => (
                        <ProductCard
                            key={index}
                            tag={tag}
                            name={name}
                            desc={desc}
                        />
                    ))}
                </div>

            </div>
        </section>
    );
}
