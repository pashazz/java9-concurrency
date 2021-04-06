# ForkJoinPool basic

This example illustrates the creation of forkjoin task

1. Создается ForkJoinPool
   
2. Реализуется задача (Task), производная от `ForkJoinTask<Void>`,
ничего не возвращающая

3. Task создает рекурсивные задачи вокруг себя и использует свой метод `invokeAll`, чтобы запустить 2 рекурсивные задачи

4. В конце все пляшут и смеются