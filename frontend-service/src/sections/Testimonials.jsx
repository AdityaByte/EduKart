import React from 'react'
import ReviewCard from '../components/ReviewCard'

const Testimonials = () => {
  let img = "https://github.com/AdityaByte.png"
  const reviews = [
    {
      clientImage: img,
      name: "Aditya Pawar",
      title: "Product Manager",
      reviewText:
        "This service exceeded my expectations. The team was professional and supportive throughout."
    },
    {
      clientImage: img,
      name: "Abhi",
      title: "Software Engineer",
      reviewText: "Amazing experience! The platform is intuitive and easy to use."
    },
    {
      clientImage: img,
      name: "Linus torvalds",
      title: "Engineer",
      reviewText:
        "I loved how smooth the whole process was. Definitely recommended!"
    }
  ]

  return (
    <div className="w-full relative overflow-hidden">
      <div className="relative text-center w-11/12 md:w-3/4 mx-auto py-20">
        <h2 className="text-[var-(--foreground-color)] text-5xl font-extrabold mb-12 drop-shadow-lg">
          Testimonials
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          {reviews.map((review, index) => (
            <ReviewCard
              key={index}
              clientImage={review.clientImage}
              name={review.name}
              title={review.title}
              reviewText={review.reviewText}
            />
          ))}
        </div>
      </div>
    </div>
  )
}

export default Testimonials
