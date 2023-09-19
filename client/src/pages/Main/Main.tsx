import Calendar from '@/components/Calendar/Calendar';
import TransactionList from '@/components/transaction/TransactionList/TransactionList';
import { useGetTransactionList } from '@/hooks/apiHooks/useGetTransactionList';
import { MainPageWrapper } from '@/pages/Main/Main.style';
import { DateType } from '@/types/date';
import { getNewDateObj } from '@/utils/getNewDateObj';
import { useEffect, useState } from 'react';

const Main = () => {
  const [date, setDate] = useState<DateType>({ ...getNewDateObj(new Date()) });
  const getTransactionListMutation = useGetTransactionList(); // 특정 날짜 조회
  const [selectedDate, setSelectedDate] = useState<DateType>(getNewDateObj(new Date())); // 거래내역 조회 시 사용

  // useEffect(() => {
  //   getTransactionListMutation.mutate({
  //     date: '고른날짜',
  //     accountNumber: 'asdf',
  //   });
  // }, [date]);

  const dummy = {
    transactions: [
      {
        transactionId: 1,
        emotion: 'emotion',
        date: '2023-09-04 09:00:00',
        transactionType: 'type',
        title: 'title',
        amount: 1000,
      },
      {
        transactionId: 2,
        emotion: 'emotion',
        date: '2023-09-05 09:00:00',
        transactionType: 'type',
        title: 'title',
        amount: 3000,
      },
    ],
  };

  const updateDate = (newDate: DateType) => setDate({ ...newDate });
  const selectCalendarDate = (select: DateType) => setSelectedDate(select);

  return (
    <MainPageWrapper>
      <Calendar updateDate={updateDate} selectCalendarDate={selectCalendarDate} />
      <TransactionList transactionDatas={dummy} />
    </MainPageWrapper>
  );
};

export default Main;
