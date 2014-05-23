PK_Q_Sort
=========

An implementation of Q sort that tries to be memory efficient

// In the code we have a sorting algorithm which was based on quick-sort.
// It is rather memory efficient. When sorting say doubles,
// it only requires one extra temp double to be stored.

// For a bit of back-ground reading: https://en.wikipedia.org/wiki/Quicksort
// In this implementation we start by setting the start element to be the pivot.
// We make a copy of that element and then start at the end of the array and
// come back through the array until we find an element that is less than the pivot.
// We then put that element into the place previously occupied by the pivot.
// Now we have an available element near the end where we can write to,
// so we then continue at the element just after the original pivot until we find an element
// greater than the pivot, we then copy that into the available space near the end.
// we then continue from near the end

// Possible extensions:
// -allow sorting to be both ascending and descending
// -make a better choice of the pivot, look into efficiency when the array is already sorted
// -could it be made generic?
// -if the arr were put into it's own class, then it need not be passes as a parameter
